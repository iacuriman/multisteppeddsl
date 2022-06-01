package job

import javaposse.jobdsl.dsl.*
import jenkins.model.*
import java.util.*;

public class DeploySpinnaker {

    static void job(dslFactory, pipelineFile, String jobName) {
        //try {
            dslFactory.pipelineJob(jobName) {
                parameters {
                    
                    def codePart = """
def hola = "holasdasdasdasdasdasdasd"
"""
                    def dockertag
                    Map<String, String> appstags = new HashMap<>()
                    appstags.put("pasito1", "cms");
                    appstags.put("pasito2", "asc");
                     //for (String key : appstags.entrySet()) 
                    //{
                     for (Map.Entry<String, String> entry : appstags.entrySet()) {
                                            dockertag = appstags.get(entry.getKey())
                                            activeChoiceParam("${entry.getKey()}") {
                                                description('Selecione si desea desplegar')
                                                choiceType('CHECKBOX')
                                                groovyScript {
                                                    script("""return ['Deploy']""")
                                                }
                                            }
                                            activeChoiceReactiveParam("ECR_IMAGE_"+"${entry.getKey()}") {
                                                description('Seleccione la imagen del ECR a desplegar')
                                                filterable()
                                                choiceType('SINGLE_SELECT')
                                                groovyScript {
                                                    script("""
${codePart}
if("${entry.getKey()}"=="Deploy")
{return getListEcrImages("eu-west-1","${entry.getValue()}"}
""")
                                                    fallbackScript('return ["error"]')
                                                }
                                                referencedParameter("${entry.getKey()}")
                                            }
                                            activeChoiceParam("Deploy_ENV_"+"${entry.getKey()}") {
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
        //catch (Exception e) {
        //    println('Error: ' + e)
       // }
   // }

}
