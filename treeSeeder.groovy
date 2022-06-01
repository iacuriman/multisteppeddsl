
import groovy.io.FileType

import jenkins.model.*
import groovy.json.*
import job.*




// CaC project location
String WORKSPACEProject = SEED_JOB.getWorkspace()
//String jobsFolderPathProject = 'pipelines'



println "WORKSPACE ${WORKSPACEProject}"
// Create folder for the project inside Jenkins
//This folder must exist inside Jenkins
def projectFolder = 'Test-422'
//This folder will be created inside Jenkins
def subprojectFolder = 'YE-ci'

//ERROR: Could not create item, unknown parent path in
// Manual creation of the folder
// folder(projectFolder)

//Creates or updates a folder. -> https://jenkinsci.github.io/job-dsl-plugin/#path/folder
def subfolder =[projectFolder,subprojectFolder].join("/")
folder(subfolder) {

    displayName(subprojectFolder)
}

folderName= "caracoli"
 jobFolder = [subfolder, folderName].join('/')
    folder(jobFolder) {
        displayName(folderName)
    }

//NodeCITest SECTION
try {
    // Checking if the pipeline file exist
    //pipelineFilePath = [pipelinesFolder, repositoryName, 'disable-ci', 'Jenkinsfile'].join('/')
    pipelineFilePath = [WORKSPACEProject,"Backend","spinaker-multiple-deploy", 'Jenkinsfile'].join('/')
    println pipelineFilePath 
    pipelineFileCI = readFileFromWorkspace(pipelineFilePath)
    println pipelineFileCI 
    jobName = [jobFolder,'DeploySpinnaker'].join('/')
    DeploySpinnaker.job(this, pipelineFileCI, jobName)
    // Remove jobName of the list to remove

} catch (Exception e) {
    println("Exception: ${e}")
}
