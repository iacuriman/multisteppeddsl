package job

import javaposse.jobdsl.dsl.*
import jenkins.model.*
import java.util.*;

public class DeploySpinnaker {

    static void job(dslFactory, pipelineFile, String jobName) {
        try {
            dslFactory.pipelineJob(jobName) {
                parameters {
                    
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
