package com.prot.gradle.plugin;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

import org.ajoberstar.grgit.Grgit;

public class VersionInfoTask extends DefaultTask {
	   private String projectName;
		private String projectBuildDir;
		private String projectResourcesFolder;
		private String version;

		private String versionInfoFile;
		
		private String getDefaultProjectName() {
			return getProject().getName();
		}
		private String getDefaultProjectBuildDir() {
			return getProject().getBuildDir().getAbsolutePath();
		}
		private String getDefaultProjectResourcesFolder() {
			return "resources/main";
		}
		private String getDefaultVersionInfoFile() {
			return "version-info.properties";
		}
		private String getDefaultVersion() {
			return getProject().getVersion().toString();
		}
		
		private String nvl(String val, String defaultIfValIsNull) {
			if (val == null || val.length() == 0) {
				return defaultIfValIsNull;
			} else {
				return val;
			}
		}
		
		public String getProjectName() {
			return projectName;
		}

		public void setProjectName(String projectName) {
			this.projectName = projectName;
		}

		public String getProjectBuildDir() {
			return projectBuildDir;
		}

		public void setProjectBuildDir(String projectBuildDir) {
			this.projectBuildDir = projectBuildDir;
		}

		public String getProjectResourcesFolder() {
			return projectResourcesFolder;
		}

		public void setProjectResourcesFolder(String projectResourcesFolder) {
			this.projectResourcesFolder = projectResourcesFolder;
		}

		public String getVersionInfoFile() {
			return versionInfoFile;
		}

		public void setVersionInfoFile(String versionInfoFile) {
			this.versionInfoFile = versionInfoFile;
		}
		
		public String getVersion() {
			return version;
		}
		public void setVersion(String version) {
			this.version = version;
		}

		@TaskAction
		void writeOut() {
			final String v               = nvl(getVersion(), getDefaultVersion());
			final String buildFolder     = nvl(getProjectBuildDir(), getDefaultProjectBuildDir());
			final String resourcesFolder = nvl(getProjectResourcesFolder(), getDefaultProjectResourcesFolder());
			final String artifactName    = nvl(getProjectName(), getDefaultProjectName());
			final String fileName        = nvl(getVersionInfoFile(), getDefaultVersionInfoFile());
			File dir = new File(buildFolder + "/" + resourcesFolder + "/versionInfo/" + artifactName);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			File file = new File(dir, fileName);
			try (FileWriter fw = new FileWriter(file); BufferedWriter bw = new BufferedWriter(fw)) {
				bw.write("Version=" + v);
				bw.newLine();
				bw.write("Revision=" + resolveRevison());
				bw.newLine();
				bw.write("Build-Time=" + now());
				bw.newLine();
				bw.write("Artificat-Name=" + artifactName);
				bw.newLine();
				bw.flush();
				fw.flush();
			} catch (IOException ex) {
				throw new RuntimeException("fail to write into '" + file.getAbsolutePath() + "'", ex);
			}
		}

		private String now() {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(new Date());
		}

		private String resolveRevison() {
			try {
				return Grgit.open().head().getId();
			} catch (Exception ex) {
				return "Error: due to " + ex.getMessage();
			}
		}
}
