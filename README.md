# jar-version-info-gradle-plugin
dynamically generate $projectName/version-info.properties for a java project when jaring because jar depends on new 
"versionInfo" task.

The implementation is to get git revision info, it can be easily changed to get svn or cvs by using their plugin, also
by specifying war or ear depends on the new "versionInfo" task, the war or ear will include $projectName/version-info.properties

