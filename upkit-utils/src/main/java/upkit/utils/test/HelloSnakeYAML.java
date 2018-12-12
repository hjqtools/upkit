package upkit.utils.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;

import org.yaml.snakeyaml.Yaml;

public class HelloSnakeYAML {

	
	public static void main(String[] args) throws FileNotFoundException {
		
		Yaml yaml = new Yaml();
        URL url = HelloSnakeYAML.class.getClassLoader().getResource("elasticsearch.yml");
        
        if (url != null) {
            Object obj = yaml.load(new FileInputStream(url.getFile()));
            System.out.println(obj);
        }
        
        
	}
}
