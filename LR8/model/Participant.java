package model;

import java.io.Serializable;

public class Participant implements Serializable {
    private static final long serialVersionUID = 1L;

    private String firstName;
    private String lastName;
    private String organization;
    private String reportTitle;
    private String section;

    // Default constructor
    public Participant() {}

    public Participant(String firstName, String lastName, String organization, String reportTitle, String section) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.organization = organization;
        this.reportTitle = reportTitle;
        this.section = section;
    }

    // Геттери та сеттери
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getOrganization() { return organization; }
    public void setOrganization(String organization) { this.organization = organization; }

    public String getReportTitle() { return reportTitle; }
    public void setReportTitle(String reportTitle) { this.reportTitle = reportTitle; }

    public String getSection() { return section; }
    public void setSection(String section) { this.section = section; }
}