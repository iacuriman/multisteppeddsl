package job

import javaposse.jobdsl.dsl.*
import jenkins.model.*
import java.util.*;

public class DeploySpinnaker {

    static void job(dslFactory, pipelineFile, String jobName) {
        //try {
            dslFactory.pipelineJob(jobName) {
                parameters {
                    /*
                    def codePart = """
def hola = "hola"
"""
*/
                    Map<String, String> appstags = new HashMap<>()
                    appstags.put("pasito1", "cms");
                    appstags.put("pasito2", "asc");
                     for (String key : appstags.entrySet()) 
                    {
 tag = bookMap.get(key)
                                            activeChoiceParam("${key}") {
                                                description('Selecione si desea desplegar')
                                                choiceType('CHECKBOX')
                                                groovyScript {
                                                    script("""return ['Deploy']""")
                                                }
                                            }
                                            activeChoiceReactiveParam("ECR_IMAGE_"+"${key}") {
                                                description('Seleccione la imagen del ECR a desplegar')
                                                filterable()
                                                choiceType('SINGLE_SELECT')
                                                groovyScript {
                                                    script("""

if("${key}"=="Deploy")
{return getListEcrImages("eu-west-1","${tag}")}
""")
                                                    fallbackScript('return ["error"]')
                                                }
                                                referencedParameter("${key}")
                                            }
                                            activeChoiceParam("Deploy_ENV_"+"${key}") {
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
    }

}
