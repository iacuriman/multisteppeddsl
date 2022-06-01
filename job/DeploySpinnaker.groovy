package job

import javaposse.jobdsl.dsl.*
import jenkins.model.*
import java.util.HashMap;
//import java.util.Map;
//import java.util.Iterator;
//import java.util.Set;

public class DeployECRSpinakerJob {

    static void job(dslFactory, pipelineFile, String jobName, environments, docker_tag ) {
        try {
            dslFactory.pipelineJob(jobName) {
                parameters {
                    def codePart = """
def hola = "hola"
"""
                    def appstags = [pasito1: "cms",
                                pasito2 : "asc",
                                 ]
                                for (HashMap.Entry<String, String> APP : appstags.entrySet()) 
                                {
                                            tag = tags["${APP}"]
                                            activeChoiceParam("${APP}") {
                                                description('Selecione si desea desplegar')
                                                choiceType('CHECKBOX')
                                                groovyScript {
                                                    script("""return ['Deploy']""")
                                                }
                                            }
                                            activeChoiceReactiveParam("ECR_IMAGE_"+"${APP}") {
                                                description('Seleccione la imagen del ECR a desplegar')
                                                filterable()
                                                choiceType('SINGLE_SELECT')
                                                groovyScript {
                                                    script("""
"${codePart}"
if("${APP}"=="Deploy")
{return getListEcrImages("eu-west-1","${tag}")}
""")
                                                    fallbackScript('return ["error"]')
                                                }
                                                referencedParameter("${APP}")
                                            }
                                            activeChoiceParam("Deploy_ENV_"+"${APP}") {
                                                description('Selecione el entorno a desplegar')
                                                choiceType('SINGLE_SELECT')
                                                groovyScript {
                                                    script("""return ['dev','stg','qa']""")
                                                }
                                            }
                                }
                }
                definition {
                    cps {
                        script(pipelineFile)
                        sandbox()
                    }
                }
            }
        }
        catch (Exception e) {
            println('Error: ' + e)
        }
    }

}
