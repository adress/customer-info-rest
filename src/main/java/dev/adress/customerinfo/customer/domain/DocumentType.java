package dev.adress.customerinfo.customer.domain;

public enum DocumentType {
    C("Cedula de Ciudadania"),
    P("Pasaporte");

    private final String description;

    DocumentType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
