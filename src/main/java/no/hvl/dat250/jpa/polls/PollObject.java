package no.hvl.dat250.jpa.polls;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PollObject {
    private String id;
    private String title;
    private List<Option> options;
}
