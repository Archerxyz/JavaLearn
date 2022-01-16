package Factory;

import java.util.HashMap;
import java.util.Map;

// 利用反射的静态工厂模式
// 扩展性极强，不违反开闭原则
// 性能差
public class FactoryByClass {

    private Map<String, Class> registeredProducts = new HashMap<>();
    private Map<String, Vehicle> registeredProductsInstance = new HashMap<>();

    public void registerVehicle(String type, Class vehicleClass) {
        registeredProducts.put(type, vehicleClass);
    }

    // 反射方法
    public Vehicle createVehicle(String type) throws InstantiationException, IllegalAccessException {
        Class productClass = registeredProducts.get(type);
        return (Vehicle)productClass.newInstance();
    }

    // 非反射方法 依赖于派生类实现
    public Vehicle createVehicleInstance(String type) {
        return registeredProductsInstance.get(type).newInstacne();
    }
}