package se.antoneliasson.attendance.gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import se.antoneliasson.attendance.models.Database;
import se.antoneliasson.attendance.models.Occasion;
import se.antoneliasson.attendance.models.Person;

public class PersonPanel extends JPanel implements ActionListener {
    private final Logger log;
    private final List<JCheckBox> boxes;
    private final List<Occasion> occasions;
    private Person person;
    private final JLabel personLabel;
    private final JCheckBox identificationChecked;
    private static final String MARK_PARTICIPATION_ACTION = "mark-participation-today";
    private static final String MARK_ID_CHECKED_ACTION = "mark-identification-checked";

    public PersonPanel(Database db) {
        super(new GridLayout(0, 1));
        log = LogManager.getLogger();
        occasions = db.listOccasions();
        boxes = new ArrayList<>();
        person = null;
        personLabel = new JLabel();
        add(personLabel);
        identificationChecked = new JCheckBox("ID checked");
        identificationChecked.setEnabled(false);
        identificationChecked.addActionListener(this);
        add(identificationChecked);
        add(new JLabel()); // poor man's field separator
        add(new JLabel("Attendance:"));
        for (int i = 0; i < occasions.size(); i++) {
            Occasion o = occasions.get(i);
            JCheckBox box = new JCheckBox(o.getDate());
            box.setEnabled(false);
            box.addActionListener(this);
            add(box);
            boxes.add(box);
        }
        assert occasions.size() == boxes.size();

        InputMap im = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = getActionMap();
        im.put(KeyStroke.getKeyStroke("ctrl SPACE"), MARK_PARTICIPATION_ACTION);
        am.put(MARK_PARTICIPATION_ACTION, new MarkParticipationAction());
        im.put(KeyStroke.getKeyStroke("ctrl pressed I"), MARK_ID_CHECKED_ACTION);
        am.put(MARK_ID_CHECKED_ACTION, new MarkIdCheckedAction());
    }

    public void refresh(Person p) {
        // This might cause data loss if I ever swap the boxes' listeners from
        // ActionListeners to ItemListeners. Better cause a NullPointerException
        // instead:
        person = p;
        
        if (p == null) {
            log.debug("Disabling controls");
            personLabel.setText("");
            for (JCheckBox box : boxes) {
                box.setEnabled(false);
                box.setSelected(false);
            }
            identificationChecked.setEnabled(false);
            identificationChecked.setSelected(false);
        } else {
            log.debug("Refreshing person panel. New person: {}", p.getId());
            personLabel.setText(p.getName());
            for (int i = 0; i < boxes.size(); i++) {
                JCheckBox box = boxes.get(i);
                Occasion o = occasions.get(i);
                box.setEnabled(true);
                boolean participates = p.getParticipation(o);
                box.setSelected(participates);
            }
            identificationChecked.setEnabled(true);
            identificationChecked.setSelected(p.getIdentificationChecked());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JCheckBox box = (JCheckBox) e.getSource();
        boolean selected = box.isSelected();
        
        if (box == identificationChecked) {
            tickId(selected);
        } else {
            Occasion o = occasions.get(boxes.indexOf(box));
            tickParticipation(o, selected);
        }
    }

    private void tickParticipation(Occasion o, boolean selected) {
        person.setParticipation(o, selected);
        log.debug("Person {} attends {}", person.getId(), o.getId());
    }

    private void tickId(boolean selected) {
        person.setIdentificationChecked(selected);
        log.debug("Identification checked for person {}: {}", person.getId(), selected);
    }

    private Occasion getCurrentOccasion() {
        // TODO: Well, this is ugly.
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String today = format.format(new Date());
        for (Occasion o : occasions) {
            if (o.getDate().equals(today)) {
                return o;
            }
        }
        throw new RuntimeException("Today is not an occasion");
    }

    class MarkParticipationAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (person != null) {
                Occasion o = getCurrentOccasion();
                log.debug("Today's occasion is {}", o.getDate());
                tickParticipation(o, !person.getParticipation(o));
                refresh(person);
            }
        }
    }

    class MarkIdCheckedAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (person != null) {
                boolean newStatus = !person.getIdentificationChecked();
                tickId(newStatus);
                refresh(person);
            }
        }
    }
}
