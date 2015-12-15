package com.ni.app;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintStream;

import org.apache.avro.Schema;
import org.apache.avro.compiler.idl.Idl;
import org.apache.avro.compiler.idl.ParseException;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception
    {
        try{
        System.out.println( "Hello World!" );
        Avdl2AvscMojo avdl2avsc = new Avdl2AvscMojo(new File("/Users/yuvaldvir/work/events/lib/events/models/"), new File("/Users/yuvaldvir/work/events/lib/events/schema/"));
        avdl2avsc.execute();
        }catch(Exception e){
            throw e;
        }
    }
}
