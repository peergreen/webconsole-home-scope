/**
 * Peergreen S.A.S. All rights reserved.
 * Proprietary and confidential.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.peergreen.webconsole.scope.home;

import java.util.Map;

import com.peergreen.webconsole.Extension;
import com.peergreen.webconsole.ExtensionPoint;
import com.peergreen.webconsole.Link;
import com.peergreen.webconsole.Scope;
import com.peergreen.webconsole.Unlink;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

/**
 * Home scope
 *
 * @author Mohammed Boukada
 */
@Extension
@ExtensionPoint("com.peergreen.webconsole.scope")
@Scope(value = "home", iconClass = "icon-home")
public class HomeScope extends VerticalLayout {

    private final static String TOP_LEFT = "top.left";
    private final static String TOP_RIGHT = "top.right";
    private final static String BOTTOM_LEFT = "bottom.left";
    private final static String BOTTOM_RIGHT = "bottom.right";

    private FrameView topLeftFrame;
    private FrameView topRightFrame;
    private FrameView bottomLeftFrame;
    private FrameView bottomRightFrame;

    public HomeScope() {
        setSizeFull();
        addStyleName("dashboard-view");

        HorizontalLayout top = new HorizontalLayout();
        top.setWidth("100%");
        top.setSpacing(true);
        addComponent(top);
        Label title = new Label("Welcome to Peergreen Administration Console");
        title.addStyleName("h1");
        top.addComponent(title);
        top.setComponentAlignment(title, Alignment.MIDDLE_LEFT);
        top.setExpandRatio(title, 1);

        HorizontalLayout row1 = new HorizontalLayout();
        row1.setSizeFull();
        row1.setMargin(new MarginInfo(true, true, true, true));
        row1.setSpacing(true);
        row1.addStyleName("row");
        addComponent(row1);
        setExpandRatio(row1, 4);

        HorizontalLayout row2 = new HorizontalLayout();
        row2.setSizeFull();
        row2.setMargin(new MarginInfo(true, true, true, true));
        row2.setSpacing(true);
        row2.addStyleName("row");
        addComponent(row2);
        setExpandRatio(row2, 4);

        topLeftFrame = new FrameView();
        row1.addComponent(topLeftFrame);

        topRightFrame = new FrameView();
        row1.addComponent(topRightFrame);

        bottomLeftFrame = new FrameView();
        row2.addComponent(bottomLeftFrame);

        bottomRightFrame = new FrameView();
        row2.addComponent(bottomRightFrame);
    }

    @Link(TOP_LEFT)
    public synchronized void addElementInTopLeftFrame(Component frame, Map properties) throws Exception {
        topLeftFrame.addFrame(frame, (String) properties.get("frame.value"));
    }

    @Unlink(TOP_LEFT)
    public synchronized void removeElementFromTopLeftFrame(Component frame) {
        topLeftFrame.removeFrame(frame);
    }

    @Link(TOP_RIGHT)
    public synchronized void addElementInTopRightFrame(Component frame, Map properties) throws Exception {
        topRightFrame.addFrame(frame, (String) properties.get("frame.value"));
    }

    @Unlink(TOP_RIGHT)
    public synchronized void removeElementFromTopRightFrame(Component frame) {
        topRightFrame.removeFrame(frame);
    }

    @Link(BOTTOM_LEFT)
    public synchronized void addElementInBottomLeftFrame(Component frame, Map properties) throws Exception {
        bottomLeftFrame.addFrame(frame, (String) properties.get("frame.value"));
    }

    @Unlink(BOTTOM_LEFT)
    public synchronized void removeElementFromBottomLeftFrame(Component frame) {
        bottomLeftFrame.removeFrame(frame);
    }

    @Link(BOTTOM_RIGHT)
    public synchronized void addElementInBottomRightFrame(Component frame, Map properties) throws Exception {
        bottomRightFrame.addFrame(frame, (String) properties.get("frame.value"));
    }

    @Unlink(BOTTOM_RIGHT)
    public synchronized void removeElementFromBottomRightFrame(Component frame) {
        bottomRightFrame.removeFrame(frame);
    }
}