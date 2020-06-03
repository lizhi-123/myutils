package config;



//@Component
public class SpringConfigLoad {

	public SpringConfigLoad() {
		ConfigLocation.getInstance().loadConfig();
	}
	

}
