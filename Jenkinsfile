def commit
def branch
def version
node('master') {
    try {
    stage('Prepare') {
        sh "rm Warp-*.zip || true"
        checkout scm
//        git 'https://github.com/WarpOrganization/warp.git'
        sh "./gradlew clean"
    }
    stage('Build') {
        sh "./gradlew build"
    }

    commit = sh(returnStdout: true, script: "git log -n 1 --pretty=format:'%h'").trim()
    branch = env.BRANCH_NAME
    version = sh(returnStdout: true, script: "cat version.txt").trim()

    stage('Test') {
        sh "./gradlew test"
        junit "*/build/test-results/*/*.xml"
    }

    def jar_steps = [:]
    jar_steps["Windows"] = {
        sh "./gradlew jar -Pplatform=windows"
        sh "mv build/libs/Warp-windows.jar Warp-windows.jar"
        sh "cp runScripts/runWindows.bat run.bat"
        sh "7z a Warp-Windows-${branch}-${version}.${commit}.zip run.bat Warp-windows.jar"
        sh "rm Warp-windows.jar run.bat"
    }
    jar_steps["Linux"] = {
        sh "./gradlew jar -Pplatform=linux"
        sh "mv build/libs/Warp-linux.jar Warp-linux.jar"
        sh "cp runScripts/runLinux.sh run.sh"
        sh "7z a Warp-Linux-${branch}-${version}.${commit}.zip run.sh Warp-linux.jar"
        sh "rm Warp-linux.jar run.sh"
    }
    jar_steps["Server"] = {
        sh "./gradlew servertest:jar"
        sh "mv servertest/build/libs/Warp-server*.jar Warp-server.jar"
        sh "cp runScripts/runServerWindows.bat runServer.bat"
        sh "cp runScripts/runServerLinux.sh runServer.sh"
        sh "7z a Warp-Server-${branch}-${version}.${commit}.zip runServer.sh runServer.bat Warp-server.jar"
        sh "rm Warp-server.jar runServer.sh runServer.bat"
    }
    stage('Generate jars') {
        parallel(jar_steps)
    }
    stage('Archive') {
        archiveArtifacts 'Warp-*.zip'
        stash includes: 'Warp-Server-*.zip', name: "server"
    }
    } catch (err) {
        currentBuild.result = 'FAILURE'
    } finally {
        stage('discord'){
            def msg = "**Status:** " + currentBuild.currentResult.toLowerCase() + "\n"
            msg += "**Changes:** \n"
            if (!currentBuild.changeSets.isEmpty()) {
                currentBuild.changeSets.first().getLogs().each {
                    msg += "- `" + it.getCommitId().substring(0, 8) + "` *" + it.getComment().substring(0, it.getComment().length()-1) + "*\n"
                }
            }
            msg += "\n **Artifacts:**\n"
            msg += "- ${env.BUILD_URL}artifacts/Warp-Windows-${branch}-${version}.${commit}.zip\n"
            msg += "- ${env.BUILD_URL}artifacts/Warp-Linux-${branch}-${version}.${commit}.zip\n"
            msg += "- ${env.BUILD_URL}artifacts/Warp-Server-${branch}-${version}.${commit}.zip\n"
            withCredentials([string(credentialsId: 'discord_webhook', variable: 'discordWebhook')]) {
                discordSend successful: currentBuild.resultIsBetterOrEqualTo('SUCCESS'), description: "${msg}", link: env.BUILD_URL, title: "${JOB_NAME} #${BUILD_NUMBER}", webhookURL: "${discordWebhook}"
            }
       }
    }

}
node('warp') {
    stage('Deploy') {
        sh "rm Warp-Server-*.zip || true"
        unstash("server")
        sh "sudo systemctl stop warp || true"
        sh "7z -y x Warp-Server-*.zip"
        sh "chmod +x run.sh"
        sh "sudo systemctl start warp"
    }
}