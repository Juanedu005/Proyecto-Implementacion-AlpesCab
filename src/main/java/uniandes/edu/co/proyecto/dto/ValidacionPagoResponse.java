package uniandes.edu.co.proyecto.dto;

public class ValidacionPagoResponse {
    private boolean tieneMedioPago;
    private String motivo;

    public ValidacionPagoResponse(boolean tieneMedioPago, String motivo) {
        this.tieneMedioPago = tieneMedioPago;
        this.motivo = motivo;
    }
    public boolean isTieneMedioPago() { return tieneMedioPago; }
    public String getMotivo() { return motivo; }
}
