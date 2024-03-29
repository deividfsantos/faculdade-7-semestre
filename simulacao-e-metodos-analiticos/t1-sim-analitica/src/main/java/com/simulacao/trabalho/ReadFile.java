package com.simulacao.trabalho;

import com.simulacao.trabalho.simulacao.Simulacao;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.representer.Representer;

import java.io.InputStream;

public class ReadFile {
    public Simulacao readYmlResource() {
        Representer representer = new Representer();
        representer.getPropertyUtils().setSkipMissingProperties(true);
        Yaml yaml = new Yaml(new Constructor(Simulacao.class), representer);
        InputStream inputStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream("fila.yml");
        return yaml.load(inputStream);
    }

}
