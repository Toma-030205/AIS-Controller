package ais.view;

import ais.model.NavStatus;

public class NavStatusView extends BaseListMenuView {
    public NavStatusView() {
        super(
                "* NAV. STATUS *",
                new String[] {
                    " UNDER WAY USING ENGINE",
                    " AT ANCHOR",
                    " NOT UNDER COMMAND",
                    " RESTRICTED MANOEUVRABILITY",
                    " CONSTRAINED BY DRAFT",
                    " MOORED",
                    " AGROUND",
                    " ENGAGED IN FISHING",
                    " UNDER WAY SAILING",
                    " RESERVED FOR HSC",
                    " RESERVED FOR WIG",
                    " NOT_DEFINED"
                },
                35,
                60
        );
    }

    public NavStatus getSelectedNavStatus() {
        return NavStatus.fromIndex(getSelectedIndex());
    }

    public String getSelectedStatus() {
        return getSelectedValue();
    }
}
