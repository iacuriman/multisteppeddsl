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
                    map.put("pasito1", "cms");
                    map.put("pasito2", "asc");
                    println "hola"
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
