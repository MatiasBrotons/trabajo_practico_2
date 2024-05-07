package ar.edu.unju.fi.ejercicio6.main;

import ar.edu.unju.fi.ejercicio6.interfaces.funcionales.*;
import ar.edu.unju.fi.ejercicio6.model.*;
public class Main {

	public static void main(String[] args) {

		FelinoDomestico gato = new FelinoDomestico("Garfield", (byte)45, 12f);
		Converter<FelinoDomestico, FelinoSalvaje> converter = x -> new FelinoSalvaje(x.getNombre(), x.getEdad(), x.getPeso());
		
		FelinoSalvaje felino1 = converter.convert(gato);
		converter.mostrarObjeto(felino1);
		
		FelinoSalvaje Nekoarc1 = new FelinoSalvaje("Tanner", (byte)20, 186);
		if(Converter.isNotNull(Nekoarc1))
		{
			Converter<FelinoSalvaje, FelinoDomestico> converter1 = x -> new FelinoDomestico(x.getNombre(), x.getEdad(), x.getPeso());
			
			FelinoDomestico Nekoarc2 = converter1.convert(Nekoarc1);
			converter1.mostrarObjeto(Nekoarc2);
		}
		else
		{
			System.out.print("es nulo");
		}
		}

}
