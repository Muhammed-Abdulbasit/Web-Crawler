Multi-Threaded Web Crawler
Group #6

A Multi-Threaded Web Crawler is a bot that can search through websites and index website content to organize site URLs. To do this, the bot takes web URLs and indexes the information on the site for the user. For our Multi-Threaded Web Crawler, we used 6 bots each with their own thread. We assigned them websites that have many URLs or hyperlinks like ABC News and Wikipedia to recursively crawl through. A separate web server is also set up for the purpose of storing the URLs to make it easier for the user to organize. With this Web Server, we have a pause and resume button to control the bots. If the project has run correctly the terminal should show each bot and its received webpage while also receiving links on the separate web server. In summary, once the user runs the project the web server will start displaying the links it finds on each website while also counting how many links it receives. The user can then choose to pause and resume the bots.

Running the Code:
To get started you first need to download the source code from GitHub
https://github.com/Muhammed-Abdulbasit/Web-Crawler 

Eclipse coding editor is strongly preferred, however if you are using VSCode you will need to install the Java coding pack
https://code.visualstudio.com/docs/languages/java

If you are using eclipse, once the project is downloaded from GitHub, open your eclipse workspace. Then click File > Import > General > Existing project into workspace. Click next. Browse to where the project was downloaded and select it. Select the project (with a checkbox) and click finish. 

On main.Java, click the run button at the top of eclipse. A servlet should automatically open up. If not then make sure you are on Main.java. On this page find the localhost URL and paste into the browser. After this is done you can run the code from Main.java, if the code is running correctly the terminal and browser will start outputting links. On the browser page, there will be a pause and resume button. These can be used to start or stop the crawler bots from searching (you may need to click the pause button twice). There will also be a receiving links counter on the browser page.
