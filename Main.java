import java.util.*;
// import java.io.*;
abstract class Gemstone {
    protected String name;
    protected double weight; // в каратах
    protected double value; // в денежном эквиваленте
    protected double transparency; // от 0 до 1
    public Gemstone(String name, double weight, double value, double transparency) {
        this.name = name;
        this.weight = weight;
        this.value = value;
        this.transparency = transparency;
    }
    public abstract String getType();
    public double getWeight() {
        return weight;
    }
    public double getValue() {
        return value;
    }
    public double getTransparency() {
        return transparency;
    }
    @Override
    public String toString() {
        return String.format("%s: %.2f ct, $%.2f, Transparency: %.2f", name, weight, value, transparency);
    }
}
class PreciousGem extends Gemstone {
    public PreciousGem(String name, double weight, double value, double transparency) {
        super(name, weight, value, transparency);
    }
    @Override
    public String getType() {
        return "Precious";
    }
}
class SemiPreciousGem extends Gemstone {
    public SemiPreciousGem(String name, double weight, double value, double transparency) {
        super(name, weight, value, transparency);
    }
    @Override
    public String getType() {
        return "Semi-Precious";
    }
}
class Necklace {
    private List<Gemstone> gemstones;
    public Necklace() {
        gemstones = new ArrayList<>();
    }
    public void addGemstone(Gemstone gem) {
        gemstones.add(gem);
    }
    public double getTotalWeight() {
        return gemstones.stream().mapToDouble(Gemstone::getWeight).sum();
    }
    public double getTotalValue() {
        return gemstones.stream().mapToDouble(Gemstone::getValue).sum();
    }
    public void sortByValue() {
        gemstones.sort(Comparator.comparingDouble(Gemstone::getValue).reversed());
    }
    public List<Gemstone> findByTransparencyRange(double min, double max) {
        List<Gemstone> result = new ArrayList<>();
        for (Gemstone gem : gemstones) {
            if (gem.getTransparency() >= min && gem.getTransparency() <= max) {
                result.add(gem);
            }
        }
        return result;
    }
    @Override
    public String toString() {
        return gemstones.toString();
    }
}
public class Main {
    public static void main(String[] args) {
        Necklace necklace = new Necklace();
        necklace.addGemstone(new PreciousGem("Diamond", 1.5, 5000, 0.9));
        necklace.addGemstone(new SemiPreciousGem("Amethyst", 2.0, 300, 0.7));
        
        System.out.println("Total Weight: " + necklace.getTotalWeight() + " ct");
        System.out.println("Total Value: $" + necklace.getTotalValue());
        
        necklace.sortByValue();
        System.out.println("Sorted Gems by Value: " + necklace);
        
        List<Gemstone> transparentGems = necklace.findByTransparencyRange(0.6, 1.0);
        System.out.println("Gems in Transparency Range: " + transparentGems);
    }
}

