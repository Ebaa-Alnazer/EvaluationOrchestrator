package com.ebaa.alnazer.problem;

import java.io.FileWriter;
import java.io.IOException;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

public class ProblemFileGenerator {
    private static Configuration configuration;
    private static final String TEMPLATES_FOLDER = "/templates";

    public static Configuration getConfiguration() {
        if(configuration == null) {
            configuration = new Configuration(Configuration.VERSION_2_3_29);
            configuration.setClassForTemplateLoading(ProblemFileGenerator.class, TEMPLATES_FOLDER);
            configuration.setDefaultEncoding("UTF-8");
            configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            configuration.setLogTemplateExceptions(false);
            configuration.setWrapUncheckedExceptions(true);
            configuration.setFallbackOnNullLoopVariable(false);
        }

        return configuration;
    }

    public static void generateFile(String templatePath, Object dataModel, String outputPath) throws IOException, TemplateException {
        Configuration configuration = getConfiguration();
        Template template = configuration.getTemplate(templatePath);

        try(FileWriter writer = new FileWriter(outputPath)) {
            template.process(dataModel, writer);
        }
    }
}
