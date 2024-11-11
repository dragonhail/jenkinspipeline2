package com.gmail.dragonhailstone.jenkinspipeline2;

import org.springframework.stereotype.Service;

@Service
public class JenkinsService {
    public int  hap(int n){
        int result = 0;
        for(int i=1; i<=n; i++){
            result += i;
        }
        return result;
    }
}