Comments:
As you can see from POM, I took the latest Selenium RC, and the latest Firefox.
The latest Firefox and GeckoDriver have issues in accessing the browser console log, and raise error "HTTP Method not allowed".
I tried several workarounds to the problem, but I hope that was not in scope for the exercise.

The problem states to dockerize the tests. I wrote a Dockerfile, built its image, and ran it. 
Please find below the steps that you can follow to do the same.


Steps to build the Docker image and run it.

$ sudo docker build --no-cache -t my-image:1 -f ./Dockerfile .
$ sudo docker run -it --rm my-image:1 /bin/sh

Run the command "mvn test" in the shell console of docker.
