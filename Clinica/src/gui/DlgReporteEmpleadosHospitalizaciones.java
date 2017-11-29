package gui;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import arreglos.ArregloEmpleado;
import arreglos.ArregloHospitalizacion;
import clases.Empleado;
import clases.Internamiento;

import java.awt.Color;
import java.awt.Font;

public class DlgReporteEmpleadosHospitalizaciones extends JDialog {

	private static final long serialVersionUID = 1L;
	private JScrollPane scrollPane;
	private JTable jtblEmpleados;
	private DefaultTableModel dtm;

	// DECLARACI�N GLOBAL DE ARREGLOS HOSPITALIZACI�N
	ArregloEmpleado ae = new ArregloEmpleado("empleados.txt");
	ArregloHospitalizacion ah = new ArregloHospitalizacion("hospitalizaciones.txt");

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		try {
			DlgReporteEmpleadosHospitalizaciones dialog = new DlgReporteEmpleadosHospitalizaciones();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DlgReporteEmpleadosHospitalizaciones() {
		getContentPane().setBackground(Color.WHITE);
		setResizable(false);
		setModal(true);
		setBounds(100, 100, 826, 553);
		setTitle("EMPLEADOS QUE REALIZARON HOSPITALIZACIONES");
		getContentPane().setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 799, 503);
		getContentPane().add(scrollPane);

		dtm = new DefaultTableModel(null, getColumnas());
		jtblEmpleados = new JTable(dtm) {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int rowIndex, int vColIndex) {
				return false;
			}

		};
		jtblEmpleados.setFont(new Font("Arial", Font.BOLD, 14));
		jtblEmpleados.setForeground(Color.BLACK);
		jtblEmpleados.setRowHeight(25);
		jtblEmpleados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(jtblEmpleados);

		formatoTabla();
		listadoPacientes();

		setLocationRelativeTo(this);
	}

	// M�TODOS VOID SIN PAR�METROS
	void formatoTabla() {
		TableColumnModel tc = jtblEmpleados.getColumnModel();
		tc.getColumn(0).setPreferredWidth(50);
		tc.getColumn(1).setPreferredWidth(150);
		tc.getColumn(2).setPreferredWidth(150);
		tc.getColumn(3).setPreferredWidth(50);

		DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
		tcr.setHorizontalAlignment(SwingConstants.CENTER);
		tc.getColumn(0).setCellRenderer(tcr);
		tc.getColumn(3).setCellRenderer(tcr);
	}

	void listadoPacientes() {

		Empleado e;
		for (int j = 0; j < ae.tama�o(); j++) {
			e = ae.obtener(j);

			Internamiento h;
			for (int j2 = 0; j2 < ah.tama�o(); j2++) {
				h = ah.obtener(j2);

				if (h.getCodEmpl() == e.getCodEmpleado()) {
					if (jtblEmpleados.getRowCount() == 0 ){
						dtm.addRow(new Object[] { e.getCodEmpleado(), e.getNombres().toUpperCase(),
								e.getApellidos().toUpperCase(), getTipoEmpleado(e.getTipoEmpleado()) });	
					}else {
						int c = 0;
						for (int i = 0; i < jtblEmpleados.getRowCount(); i++) {
							if (e.getCodEmpleado() == Integer.parseInt(jtblEmpleados.getValueAt(i, 0).toString())){
								c++;
								break;
							}
						}
						
						if (c == 0) {
							dtm.addRow(new Object[] { e.getCodEmpleado(), e.getNombres().toUpperCase(),
									e.getApellidos().toUpperCase(), getTipoEmpleado(e.getTipoEmpleado()) });
						}
					}
				}
			}
		}

	}

	// M�TODOS CON RETORNO SIN PAR�METROS
	String[] getColumnas() {
		String columnas[] = new String[] { "C�DIGO EMPLEADO", "NOMBRES", "APELLIDOS", "TIPO" };
		return columnas;
	}

	// M�TODOS CON RETORNO CON PAR�METROS
	String getTipoEmpleado(int tipo) {
		if (tipo == 0) {
			return "ADMINISTRADOR";
		}
		else {
			return "CAJERO";
		}
	}

}
