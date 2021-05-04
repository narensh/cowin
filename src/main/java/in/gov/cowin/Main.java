package in.gov.cowin;

import in.gov.cowin.helpers.JsonBodyHandler;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    private static String BASE_URL = "https://cdn-api.co-vin.in/api/v2/appointment/sessions/public/calendarByPin";
    private static HttpClient client = HttpClient.newHttpClient();
    private static int MIN_AGE_LIMIT = 18;
    private static int PIN_CODE_START = 110091;
    private static int PIN_CODE_END = 110092;
    private static int NO_OF_DAYS_TO_SEARCH_FOR = 5;

    public static void main(String[] args) throws IOException, InterruptedException {
        MIN_AGE_LIMIT = parseInt(args, 0, MIN_AGE_LIMIT);
        PIN_CODE_START = parseInt(args, 1, PIN_CODE_START);
        PIN_CODE_END = parseInt(args, 2, PIN_CODE_END);
        NO_OF_DAYS_TO_SEARCH_FOR = parseInt(args, 3, NO_OF_DAYS_TO_SEARCH_FOR);

        System.out.println("Usage Instructions:");
        System.out.println("gradle run --args=\"<min_age_limit:18 or 45> <pincode_start_range> <pincode_end_range> <no_of_days_to_search_for>\"\n");


        List<Center> availableCenters = findCentersForTheRange();
        availableCenters.stream().forEach(System.out::println);
    }

    private static int parseInt(String[] args, int i, int defaultValue) {
        try {
            return Integer.parseInt(args[i]);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    private static List<Center> findCentersForTheRange() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-YYYY");
        LocalDate today = LocalDate.now();
        List<Center> availableCenters = new ArrayList<>();
        IntStream.range(PIN_CODE_START, PIN_CODE_END + 1).asLongStream().forEach(pinCode -> {
            IntStream.range(0, NO_OF_DAYS_TO_SEARCH_FOR).asLongStream().forEach(days -> {
                String forDate = today.plusDays(days).format(dateTimeFormatter);
                System.out.println("checking at pinCode: " + pinCode + " for date: " + forDate);
                try {
                    availableCenters.addAll(findCenters(forDate, pinCode));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        });
        return availableCenters;
    }

    private static List<Center> findCenters(String forDate, long pinCode) throws IOException, InterruptedException {
        String url = BASE_URL + "?" + "date=" + forDate + "&pincode=" + pinCode;
        System.out.println("Url: " + url);
        HttpRequest request = HttpRequest.newBuilder(URI.create(url)).header("accept", "application/json").build();
        var response = client.send(request, new JsonBodyHandler<>(CoWinResponse.class));
        CoWinResponse coWinResponse = response.body().get();

        List<Center> availableCenters = coWinResponse.getCenters().stream()
                .filter(center -> {
                    List<Session> matchingSessions = center.getSessions().stream()
                            .filter(session -> {
                                return session.getAvailableCapacity() > 0
                                        && session.getMinAgeLimit() == MIN_AGE_LIMIT;
                            }).collect(Collectors.toList());
                    center.setMatchingSessions(matchingSessions);
                    return (matchingSessions.size() > 0);
                })
                .collect(Collectors.toList());
        return availableCenters;
    }
}
