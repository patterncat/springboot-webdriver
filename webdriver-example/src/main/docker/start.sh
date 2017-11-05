#!/bin/sh
{
Xvfb :10 -ac &
export DISPLAY=:10
} || { # catch
   echo 'xvfb error'
}
java -Djava.security.egd=file:/dev/./urandom -jar /app.jar