import java.util.*;

public class HTMLManager {
    private Queue<HTMLTag> tags;

    public HTMLManager(Queue<HTMLTag> html) {
        if (html == null) {
            throw new IllegalArgumentException("HTML queue cannot be null");
        }
        tags = new LinkedList<HTMLTag>(html);
    }

    public Queue<HTMLTag> getTags() {
        return tags;
    }
    public String toString() {
        StringBuilder result = new StringBuilder();
        int size = tags.size();

        for (int i = 0; i < size; i++) {
            HTMLTag tag = tags.remove();
            result.append(tag.toString().trim());
            tags.add(tag);
        }

        return result.toString();
    }
    public void fixHTML() {
        Stack<HTMLTag> openTags = new Stack<HTMLTag>();
        int originalSize = tags.size();

        for (int i = 0; i < originalSize; i++) {
            HTMLTag tag = tags.remove();

            if (tag.isSelfClosing()) {
                tags.add(tag);
            } else if (tag.isOpening()) {
                tags.add(tag);
                openTags.push(tag);
            } else if (tag.isClosing() && !openTags.isEmpty()) {
                if (openTags.peek().matches(tag)) {
                    tags.add(tag);
                    openTags.pop();
                } else {
                    tags.add(openTags.pop().getMatching());
                }
            }
            // Discard any closing tag that has no opening tag.
        }

        while (!openTags.isEmpty()) {
            tags.add(openTags.pop().getMatching());
        }
    }
}

/*
# PROGRAM OUTPUT
===============================
Processing tests/test2.html...
===============================
HTML: <a><a><a></a>
Checking HTML for errors...
HTML after fix: <a><a><a></a></a></a>
----> Result matches Expected Output!

===============================
Processing tests/test1.html...
===============================
HTML: <b><i><br /></b></i>
Checking HTML for errors...
HTML after fix: <b><i><br /></i></b>
----> Result matches Expected Output!

===============================
Processing tests/test4.html...
===============================
HTML: <div><div><ul><li></li><li></li><li></ul></div>
Checking HTML for errors...
HTML after fix: <div><div><ul><li></li><li></li><li></li></ul></div></div>
----> Result matches Expected Output!

===============================
Processing tests/test3.html...
===============================
HTML: <br /></p></p>
Checking HTML for errors...
HTML after fix: <br />
----> Result matches Expected Output!

===============================
Processing tests/test5.html...
===============================
HTML: <div><h1></h1><div><img /><p><br /><br /><br /></div></div></table>
Checking HTML for errors...
HTML after fix: <div><h1></h1><div><img /><p><br /><br /><br /></p></div></div>
----> Result matches Expected Output!

===============================
        All tests passed!
===============================
*/
