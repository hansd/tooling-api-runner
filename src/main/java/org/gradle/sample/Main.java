package org.gradle.sample;

import org.gradle.tooling.BuildLauncher;
import org.gradle.tooling.GradleConnector;
import org.gradle.tooling.ProjectConnection;

import java.io.File;

/**
 * 0: Project path
 * 1: Tasknames
 * 2: Number of runs
 * 3: Parallel [true/false]
 * 4: Gradle Installation dir (optional)
 * 5: Grade user home dir (optional)
 */
public class Main {
    public static void main(String[] args) {
        // Configure the connector and create the connection
		long connectionStart = System.currentTimeMillis();
        // Configure the connector and create the connection	
        GradleConnector connector = GradleConnector.newConnector();

        if (args.length > 4) {
            connector.useInstallation(new File(args[4]));
            if (args.length > 5) {
                connector.useGradleUserHomeDir(new File(args[5]));
            }
        }
		
		String projectPath = args[0];
        connector.forProjectDirectory(new File(projectPath));

		System.out.println("Pre-Connection Time: " + (System.currentTimeMillis() - connectionStart)/1000.0);
		
		ProjectConnection connection = connector.connect();
		System.out.println("Connection Time: " + (System.currentTimeMillis() - connectionStart)/1000.0);
		      
        try {
			double overallDuration = 0;
            // Configure the build
			int noOfRuns = Integer.parseInt(args[2]);
			boolean parallel = Boolean.parseBoolean(args[3]);
			String taskName = args[1];
            for (int i = 0; i < noOfRuns; i++) {
                BuildLauncher launcher = connection.newBuild();	
				if (parallel) {
					launcher.withArguments("--parallel");
				}
				//launcher.withArguments("--offline");
				//launcher.setJvmArguments("-Dcom.android.build.gradle.overrideVersionCheck=true", "-Xmx2048m", "-XX:MaxPermSize=512m");
                launcher.forTasks(taskName);
                launcher.setStandardOutput(System.out);
                launcher.setStandardError(System.err);

                // Run the build
                long begin = System.currentTimeMillis();
                launcher.run();
				double duration = (System.currentTimeMillis() - begin)/1000.0;
                System.out.println("Time: " + duration);
				if (i > 0) {
					overallDuration += duration;
				}
            }
			System.out.println("Average Time: " + overallDuration/(noOfRuns - 1));
            
        } finally {
            // Clean up
            connection.close();
        }
    }
}


