/**
 * JetBrains Space Automation
 * This Kotlin script file lets you automate build activities
 * For more info, see https://www.jetbrains.com/help/space/automation.html
 */

job("Build and run tests") {
    container(displayName = "Run mvn install", image = "maven:latest") {
        shellScript {
            content = """
	            mvn clean package -Dmaven.test.skip=true -B
                ls -al
            """
        }

        fileArtifacts {
            localPath = "~/test/build.zip"
            remotePath = "./d3code-admin/target"
            onStatus = OnStatus.SUCCESS
        }
    }
}
