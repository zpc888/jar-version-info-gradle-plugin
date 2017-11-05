/*
 * Copyright © 2014-2017 Capco. All rights reserved.
 * Capco Digital Framework.
 */
package com.prot.gradle.plugin;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class PackageVersionInfoPlugin implements Plugin<Project> {
	@Override
	public void apply(Project project) {
		project.getTasks().create("versionInfo", VersionInfoTask.class, (task) -> {
			task.setVersionInfoFile("version-info.properties");
		});		
	}
}
