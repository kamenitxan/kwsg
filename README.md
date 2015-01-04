Aplication for generating forum signatures for WoW player.
It takes data from Blizzard API.

![Screenshot](https://dl.dropboxusercontent.com/u/3121397/Sn%C3%ADmek%20obrazovky%202014-11-17%20v%C2%A017.57.27.png)

Text mode:

Run jar from terminal with name and realm as parameters
`java -jar name realm`. File will be saved to jar file location.

Example:
`java -jar Kamenitxania Thunderhorn`

Javadoc:
http://kamenitxan.github.io/kwsg/

Generators class queries data from Blizzard API, creates image (backgrounds are in images package) and saves it to disk.
Gui package contains gui. I'm using my SceneSwitcher library https://github.com/kamenitxan/SceneSwitcher