mvn clean package -Dmaven.test.skip=true

docker run --rm webdriver-example:0.0.5 /bin/bash