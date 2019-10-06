package com.simulacao.trabalho;

import com.simulacao.trabalho.simulacao.Simulacao;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.InputStream;

public class ReadFile {
    public Simulacao readYmlResource() {
        Yaml yaml = new Yaml(new Constructor(Simulacao.class));
        InputStream inputStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream("fila.yml");
        return yaml.load(inputStream);
    }

}
