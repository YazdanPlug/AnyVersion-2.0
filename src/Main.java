package ir.yzplug.anyversion;

//import packages
import org.cloudburstmc.server.network.ProtocolInfo;
import org.cloudburstmc.server.plugin.PluginBase;
import org.cloudburstmc.server.utils.TextFormat;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;

public class AnyVersion extends PluginBase {

    @Override
    public void onEnable() {
    	//the message for enable plugin
    	this.getLogger().info(TextFormat.YELLOW + "AnyVersion plugin loaded (for api 2.0(CloudBurstMC))");
    	
        saveDefaultConfig();
        
        List<Integer> versions = getConfig().getIntegerList("Versions");
        
        Class<?> clas = ProtocolInfo.class;
        
        try {
        	
            Field f1 = clas.getDeclaredField("CURRENT_PROTOCOL");
            
            f1.setAccessible(true);
            
            int currentProtocol = f1.getInt(null);
            
            getLogger().debug("Current protocol: " + currentProtocol);
            
            versions.add(currentProtocol);
            
            getLogger().debug("Versions: " + versions.toString());
            
            Field fail = clas.getDeclaredField("SUPPORTED_PROTOCOLS");
            
            fail.setAccessible(true);
            
            
            Field m = Field.class.getDeclaredField("modifiers");
            
            m.setAccessible(true);
            
            m.setInt(fail, fail.getModifiers() & ~Modifier.FINAL);
            
            fail.set(fail, versions);
            
            getLogger().debug("Set: " + fail.get(null).toString());
            
        } catch (NoSuchFieldException | IllegalAccessException event) {
        	
            event.printStackTrace();
            
            return;
        }
        
    }
}
