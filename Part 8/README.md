Hi,

To work in this program I've used "http://gorest.co.in" that would requires a bearer token that you can generate by signing up on this website. Once you register yourself.

You can request to generate token for you and then you can use the same token in the program by replacing the one I've used in program.

FIDDLER Proxy Configuration:

1. Go to below mentioned URLs as per your operating system.<br>
--- Windows : https://www.telerik.com/download/fiddler
--- MAC : https://www.telerik.com/download/fiddler-everywhere
2. Please enter Name if asked, email and Country and accept terms
3. Download application and install.
4. Once installation is done then open and go to settings on right corner cog.
5. Select option and check for port number. You can use predefined one else you can define your own and save.
6. Once done then goto your scala script and use below mentioned code. Token and baseUrl in your case will be different 

val httpConf = http.proxy(Proxy("localhost", port = 8888)) // This is Fiddler's proxy configuration
    .baseUrl("http://gorest.co.in")
    .header("Authorization", "Bearer dba6e3d77bc25db0972b7dbcf60af6b7fbbff7bf30f3eb1d3e24e5193e165ab6")tioned configuration in http configuration.

7. Write your test script and then execute
8. As soon as you select the script in terminal and enter desctiption fiddler will start recording all request sent by gatling and you can check realtime request that are being sent.
Please refer attached screenshot for same from script - "BasicLoadSimulationFiddler.scala"

Enjoy!
