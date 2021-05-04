#cowin
- search for a wider range of locations and dates while focusing upon centers with available slots
> Usage Instructions below. Also, checkout SampleResponse.png for screenshot

## Build Jar
```
gradle shadowJar
``` 
## Run with Jar
```
java -jar build/libs/cowin-1.0-SNAPSHOT-all.jar in.gov.cowin.Main 18 110091 110093 14
```

## Run with Gradle
```
gradle run --args="<min_age_limit:18 or 45> <pincode_start_range> <pincode_end_range> <no_of_days_to_search_for>"
```

## Supported filter criterions
- min_age_limit = 18 or 45
- pincode_start_range = 110091
- pincode_end_range = 110093
- no_of_days_to_search_for = 14

```
gradle run --args="18 110091 110093 14"
```