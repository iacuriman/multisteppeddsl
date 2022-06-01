package job

import javaposse.jobdsl.dsl.*
import jenkins.model.*
import java.util.*;

public class DeploySpinnaker {

    static void job(dslFactory, pipelineFile, String jobName) {
        try {
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
