package com.ni.app;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.FileFilter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

import org.apache.avro.Schema;
import org.apache.avro.compiler.idl.Idl;
import org.apache.avro.compiler.idl.ParseException;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * Generates Avro Schema from Avro IDL files.
 *
 * @author sameerbhadouria
 *
 */
@Mojo(name="genschema")
public class Avdl2AvscMojo extends org.apache.maven.plugin.AbstractMojo {

	@Parameter(defaultValue="/Users/yuvaldvir/work/events/lib/events/models/")
	private File inputAvdlDirectory;

	@Parameter(defaultValue="/Users/yuvaldvir/work/events/lib/events/schema/")
	private File outputSchemaDirectory;
        
        public Avdl2AvscMojo(File inputAvdlDirectory, File outputSchemaDirectory) {
            super();
            this.inputAvdlDirectory = inputAvdlDirectory;
            this.outputSchemaDirectory = outputSchemaDirectory;

        }

	/*
	 * (non-Javadoc)
	 * @see org.apache.maven.plugin.AbstractMojo#execute()
	 */
	public void execute() throws MojoExecutionException, MojoFailureException {

		if (!inputAvdlDirectory.isDirectory()) {
                     
			throw new MojoExecutionException("inputAvdlDirectory: "
					+ inputAvdlDirectory.toString() + " is not a directory");
		}               
                
		if (!outputSchemaDirectory.exists()) {
			outputSchemaDirectory.mkdirs();
		}
		else if (!outputSchemaDirectory.isDirectory()){
			throw new MojoExecutionException("outputSchemaDirectory: "
					+ outputSchemaDirectory.toString() + " is not a directory");
		}

		getLog().info("Finding .avdl files in directory: " + inputAvdlDirectory.toString());
		File[] avdlFiles = getAllFiles(inputAvdlDirectory);

		if (avdlFiles != null) {
			for (File avdlFile : avdlFiles) {
				generateSchema(avdlFile);
			}
		}

	}

    private File[] getAllFiles(File directory) {
        File[] avdlFiles = directory.listFiles(new FilenameFilter() {
            /*
            * (non-Javadoc)
            * @see java.io.FilenameFilter#accept(java.io.File, java.lang.String)
            */
            public boolean accept(File dir, String name) {
                return name.endsWith(".avdl");
            }
        });
        
        ArrayList<File> fileList = new ArrayList<File>();
        
         for(File f : avdlFiles){
            fileList.add(f);
         }
         
        File[] directories = directory.listFiles(new FileFilter() {
            public boolean accept(File dir) {
                return dir.isDirectory();
            }
        });
        for(File d : directories){
            File[] files = getAllFiles(d);
            for(File f : files){
            fileList.add(f);
         }
        }
        
        
        return fileList.toArray(new File[fileList.size()]);
    }

	/**
	 * Generates the Avro schema avsc file from the specified avdl file.
	 *
	 * @param avdlFile
	 * @throws MojoExecutionException
	 */
	private void generateSchema(File avdlFile) throws MojoExecutionException {
		Idl idlParser = null;
		getLog().info("Found avdl file: " + avdlFile.getPath());
		try {
			idlParser = new Idl(avdlFile);

			for (Schema schema : idlParser.CompilationUnit().getTypes()) {
				String dirpath = outputSchemaDirectory.getAbsolutePath();
			    String filename = dirpath + "/" + schema.getName() + ".avsc";
			    getLog().info("Creating schema file: " + filename);
			    FileOutputStream fileOutputStream = new FileOutputStream(filename);
			    PrintStream printStream = new PrintStream(fileOutputStream);
			    printStream.println(schema.toString(true));
			    printStream.close();
			}
		}
		catch (IOException e) {
			getLog().error("Failed to generate Avro schema for file: " + avdlFile.getName(), e);
			throw new MojoExecutionException(e.getMessage());
		}
		catch (ParseException e) {
			getLog().error("Failed to generate Avro schema for file: " + avdlFile.getName(), e);
			throw new MojoExecutionException(e.getMessage());
		}
		finally {
			if (idlParser != null) {
				try {
					idlParser.close();
				}
				catch (IOException e) {
					getLog().error("Erro closing IDL Parser.", e);
				}
			}
		}
	}

}