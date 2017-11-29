package arreglos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

import clases.Servicio;

public class ArregloServicio {

	// Atributo privado
	private ArrayList<Servicio> ser;
	private String archivo;

	// Constructor
	public ArregloServicio(String archivo) {
		ser = new ArrayList<Servicio>();
		this.archivo = archivo;
		cargarServicios();
	}

	// Operaciones p�blicas
	public void adicionar(Servicio s) {
		ser.add(s);
	}

	public Servicio obtener(int pos) {
		return ser.get(pos);
	}

	public int tama�o() {
		return ser.size();
	}

	public void eliminar(Servicio x) {
		ser.remove(x);
	}

	public Servicio buscar(int CodServicio) {
		Servicio x;
		for (int i = 0; i < tama�o(); i++) {
			x = obtener(i);
			if (x.getCodServicio() == CodServicio)
				return x;
		}
		return null;
	}

	public int codigoCorrelativo() {
		if (tama�o() == 0)
			return 10000001;
		else
			return obtener(tama�o() - 1).getCodServicio() + 1;
	}
	
	public String getArchivo() {
		return archivo;
	}

	public void eliminarAlFinal() {
		if (tama�o() > 0)
			ser.remove(tama�o() - 1);
	}

	public void eliminarTodo() {
		if (tama�o() > 0)
			ser.clear();
	}

	public void grabarServicios() {
		try {
			PrintWriter pw;
			String linea;
			Servicio x;
			pw = new PrintWriter(new FileWriter(archivo));
			for (int i = 0; i < tama�o(); i++) {
				x = obtener(i);
				linea = x.getCodServicio() + ";" + x.getDescripcion() + ";" + x.getTipo() + ";" + 
				x.getPrecio();
				pw.println(linea);
			}
			pw.close();
		}
		catch (Exception e) {
		}
	}

	public void cargarServicios() {
		try {
			BufferedReader br;
			String linea, s[], descripcion;
			int codigo, tipo;
			double precio;
			Servicio nuevo;
			br = new BufferedReader(new FileReader(archivo));
			while ((linea = br.readLine()) != null) {
				s = linea.split(";");
				codigo = Integer.parseInt(s[0].trim());
				descripcion = s[1].trim();
				tipo = Integer.parseInt(s[2].trim());
				precio = Double.parseDouble(s[3].trim());

				nuevo = new Servicio(codigo, descripcion, tipo, precio);
				adicionar(nuevo);
			}
			br.close();
		}
		catch (Exception e) {
		}
	}

	public boolean existeArchivo() {
		File f = new File(archivo);
		return f.exists();
	}

}
