def branch = BRANCH_NAME
def version
try {
    node('master') {
        stage('Prepare') {
            properties([buildDiscarder(logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '', daysToKeepStr: '', numToKeepStr: '20')),
                        disableConcurrentBuilds()])
            sh "rm Warp-*.zip || true"
            sh "rm Warp-*.jar || true"
            checkout scm
            sh "./gradlew clean"
        }
        
        version = sh(returnStdout: true, script: "cat version.txt").trim()
        
        stage('Build') {
            sh "./gradlew build"
        }

        stage('Test') {
            sh "./gradlew test"
            junit "*/build/test-results/*/*.xml"
        }

        def jar_steps = [:]
        jar_steps["Windows"] = {
            sh "./gradlew servertest:jar -Pplatform=windows"
            sh "mv servertest/build/libs/Warp-windows-*.jar Warp-windows.jar"
            sh "cp runScripts/runWindows.bat run.bat"
            sh "7z a Warp-Windows-${branch}-${version}.zip run.bat Warp-windows.jar"
            sh "7z t Warp-windows.jar"
            sh "7z t Warp-Windows-${branch}-${version}.zip"
            sh "rm Warp-windows.jar run.bat"
        }
        jar_steps["Linux"] = {
            sh "./gradlew servertest:jar -Pplatform=linux"
            sh "mv servertest/build/libs/Warp-linux-*.jar Warp-linux.jar"
            sh "cp runScripts/runLinux.sh run.sh"
            sh "7z a Warp-Linux-${branch}-${version}.zip run.sh Warp-linux.jar"
            sh "7z t Warp-linux.jar"
            sh "7z t Warp-Linux-${branch}-${version}.zip"
            sh "rm Warp-linux.jar run.sh"
        }
        jar_steps["Server"] = {
            sh "./gradlew servertest:jar -Pserver=true"
            sh "mv servertest/build/libs/Warp-server-*.jar Warp-server.jar"
            sh "cp runScripts/runServerWindows.bat runServer.bat"
            sh "cp runScripts/runServerLinux.sh runServer.sh"
            sh "7z a Warp-Server-${branch}-${version}.zip runServer.sh runServer.bat Warp-server.jar"
            sh "7z t Warp-server.jar"
            sh "7z t Warp-Server-${branch}-${version}.zip"
            sh "rm Warp-server.jar runServer.sh runServer.bat"
        }
        stage('Generate jars') {
            parallel(jar_steps)
        }
        stage('Archive') {
            archiveArtifacts 'Warp-*.zip'
            stash includes: 'Warp-Server-*.zip', name: "server"
        }


    }
    node('warp') {
        stage('Deploy') {
            if (branch == "master") {
                sh "rm Warp-Server-*.zip || true"
                unstash("server")
                sh "sudo systemctl stop warp || true"
                sh "7z -y x Warp-Server-*.zip"
                sh "7z t Warp-server.jar"
                sh "chmod +x runServer.sh"
                sh "sudo systemctl start warp"
            }
        }
    }
} catch (err) {
    currentBuild.result = 'FAILURE'
} finally {
    stage('Discord notify'){
        def artifactUrl = env.BUILD_URL + "artifact/"
        def msg = "**Status:** " + currentBuild.currentResult.toLowerCase() + "\n"
        msg += "**Branch:** ${branch}\n"
        msg += "**Changes:** \n"
        if (!currentBuild.changeSets.isEmpty()) {
            currentBuild.changeSets.first().getLogs().each {
                msg += "- `" + it.getCommitId().substring(0, 8) + "` *" + it.getComment().substring(0, it.getComment().length()-1) + "*\n"
            }
        } else {
            msg += "no changes for this run\n"
        }

        if (msg.length() > 1024) msg.take(msg.length() - 1024)

        def filename
        msg += "\n **Artifacts:**\n"
        currentBuild.rawBuild.getArtifacts().each {
            filename = it.getFileName()
            msg += "- [${filename}](${artifactUrl}${it.getFileName()})\n"
        }

        withCredentials([string(credentialsId: 'discord_webhook', variable: 'discordWebhook')]) {
            discordSend thumbnail: "https://static.kocproz.ovh/warp-logo.png", successful: currentBuild.resultIsBetterOrEqualTo('SUCCESS'), description: "${msg}", link: env.BUILD_URL, title: "Warp_Engine:${branch} #${BUILD_NUMBER}", webhookURL: "${discordWebhook}"
        }
    }
}
