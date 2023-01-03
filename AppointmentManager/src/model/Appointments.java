package model;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Eric Trevorrow
 */

/** This class creates the Appointments objects.*/
public class Appointments {
    private int appointmentId;
    private String title;
    private String description;
    private String location;
    private String type;
    private int customerId;
    private int userId;
    private int contactId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");

    public Appointments(int appointmentId, String title, String description, String location, String type, int customerId, int userId, int contactId,
                        LocalDateTime startDate, LocalDateTime endDate){
        this.appointmentId = appointmentId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**This method finds and returns the ID of an appointment.
     * Gets the ID of the appointment.
     * @return Returns the Appointment ID.*/
    public int getAppointmentId(){
        return appointmentId;
    }

    /**This method finds and returns the title of an appointment.
     * Gets the title of the appointment.
     * @return Returns the Appointment title.*/
    public String getTitle(){
        return title;
    }

    /**This method finds and returns the description of an appointment.
     * Gets the description of the appointment.
     * @return Returns the Appointment description.*/
    public String getDescription(){
        return description;
    }

    /**This method finds and returns the location of an appointment.
     * Gets the location of the appointment.
     * @return Returns the Appointment location.*/
    public String getLocation(){
        return location;
    }

    /**This method finds and returns the type of an appointment.
     * Gets the type of the appointment.
     * @return Returns the Appointment type.*/
    public String getType(){
        return type;
    }

    /**This method finds and returns the ID of a customer in the appointment list.
     * Gets the ID of a customer in the appointment list.
     * @return Returns the Customer ID.*/
    public int getCustomerId(){
        return customerId;
    }

    /**This method finds and returns the ID of a user in the appointment list.
     * Gets the ID of a user in the appointment list.
     * @return Returns the User ID.*/
    public int getUserId(){
        return userId;
    }

    /**This method finds and returns the ID of a contact in the appointment list.
     * Gets the ID of a contact in the appointment list.
     * @return Returns the Contact ID.*/
    public int getContactId(){
        return contactId;
    }

    /**This method finds and returns a timestamp of the appointments starting date and time.
     * Gets the start date and time from an appointment in the appointment list and converts it into a formatted string.
     * @return Returns the Start Date.*/
    public String getStartDate(){

        ZonedDateTime zoneDate = startDate.atZone(ZoneId.of(ZoneId.systemDefault().toString()));
        ZonedDateTime toUtcZoneDate = zoneDate.withZoneSameInstant(ZoneId.of("UTC"));
        LocalDateTime toUtcLocalDate = toUtcZoneDate.toLocalDateTime();

        ZonedDateTime localToZone = toUtcLocalDate.atZone(ZoneId.of("UTC"));
        ZonedDateTime ZoneToLocal = localToZone.withZoneSameInstant(ZoneId.of(ZoneId.systemDefault().toString()));
        LocalDateTime zoneStartDate = ZoneToLocal.toLocalDateTime();

        String s = dtf.format(zoneStartDate);
        return s;
    }

    /**This method finds and returns a timestamp of the appointments ending date and time.
     * Gets the end date and time from an appointment in the appointment list and converts it into a formatted string.
     * @return Returns the End Date.*/
    public String getEndDate(){

        ZonedDateTime zoneDate = endDate.atZone(ZoneId.of(ZoneId.systemDefault().toString()));
        ZonedDateTime toUtcZoneDate = zoneDate.withZoneSameInstant(ZoneId.of("UTC"));
        LocalDateTime toUtcLocalDate = toUtcZoneDate.toLocalDateTime();

        ZonedDateTime localToZone = toUtcLocalDate.atZone(ZoneId.of("UTC"));
        ZonedDateTime ZoneToLocal = localToZone.withZoneSameInstant(ZoneId.of(ZoneId.systemDefault().toString()));
        LocalDateTime zoneEndDate = ZoneToLocal.toLocalDateTime();

        String e = dtf.format(zoneEndDate);
        return e;
    }
}
