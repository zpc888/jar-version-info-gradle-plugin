# jar-version-info-gradle-plugin
dynamically generate $projectName/version-info.properties for a java project when jaring because jar depends on new 
"versionInfo" task.

The implementation is to get git revision info, it can be easily changed to get svn or cvs by using their plugin, also
by specifying war or ear depends on the new "versionInfo" task, the war or ear will include versionInfo/$projectName/version-info.properties

To use this plugin, please see: https://plugins.gradle.org/plugin/com.prot.versioninfo

In short, for single project with gradle version greater than 2.1, using:
plugins {
  id "com.prot.versioninfo" version "0.2"
}

add after "apply plugin: 'java'" line, adding:
jar.dependsOn versionInfo

For multi-project application, at root project, using:

plugins {
  id "com.prot.versioninfo" version "0.2" apply false
}

and then:
allprojects {
   //after "apply plugin: 'java'" line, adding:
   apply plugin 'com.prot.versioninfo'
   jar.dependsOn versionInfo
}

or (if only want to capture sub-projects version info)
subprojects {
   //after "apply plugin: 'java'" line, adding:
   apply plugin 'com.prot.versioninfo'
   jar.dependsOn versionInfo
}

or (if only want to capture project name starts with 'xyz')
configure( allprojects.findAll { it.name.startsWith("xyz") } ) {
   //after "apply plugin: 'java'" line, adding:
   apply plugin 'com.prot.versioninfo'
   jar.dependsOn versionInfo
}

or (another way)
allprojects { prj ->
   if (prj.name.startsWith("xyz")) {
	   apply plugin 'com.prot.versioninfo'
	   jar.dependsOn versionInfo
   }
}

See https://github.com/zpc888/api-using-version-info how to use the
generated version info at runtime via restful api in spring platform

