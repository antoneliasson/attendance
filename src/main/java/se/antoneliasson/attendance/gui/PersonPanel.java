package se.antoneliasson.attendance.gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
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
    
    public PersonPanel(Database db) {
        super(new GridLayout(0, 1));
        log = LogManager.getLogger();
        occasions = db.listOccasions();
        boxes = new ArrayList<>();
        person = null;
        personLabel = new JLabel();
        add(personLabel);
        identificationChecked = new JCheckBox("ID checked", false);
        identificationChecked.addActionListener(this);
        add(identificationChecked);
        add(new JLabel()); // poor man's field separator
        add(new JLabel("Attendance:"));
        for (int i = 0; i < occasions.size(); i++) {
            Occasion o = occasions.get(i);
            JCheckBox box = new JCheckBox(o.getDate(), false);
            box.setEnabled(false);
            box.addActionListener(this);
            add(box);
            boxes.add(box);
        }
    }

    public void refresh(Person p) {
        log.debug("Refreshing person panel. New person: {}", p.getId());
        person = p;
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

    @Override
    public void actionPerformed(ActionEvent e) {
        JCheckBox box = (JCheckBox) e.getSource();
        boolean selected = box.isSelected();
        
        if (box == identificationChecked) {
            person.setIdentificationChecked(selected);
            log.debug("Identification checked for person {}: {}", person.getId(), selected);
        } else {
            Occasion o = occasions.get(boxes.indexOf(box));
            person.setParticipation(o, selected);
            log.debug("Person {} attends {}", person.getId(), o.getId());
        }
    }
}
