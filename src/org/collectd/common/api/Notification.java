/*
 * jcollectd
 * Copyright (C) 2009 Hyperic, Inc.
 * 
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the
 * Free Software Foundation; only version 2 of the License is applicable.
 * 
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin St, Fifth Floor, Boston, MA  02110-1301 USA
 */

package org.collectd.common.api;

/**
 * Java representation of collectd/src/plugin.h:notfication_t structure.
 */
public class Notification extends PluginData {
    private static final int FAILURE = 1;
    private static final int WARNING = 2;
    private static final int OKAY = 4;

    public static final String[] SEVERITY = {
            "FAILURE",
            "WARNING",
            "OKAY",
            "UNKNOWN"
    };

    private int _severity;
    private String _message;

    public Notification(PluginData pd) {
        super(pd);
    }

    public void setSeverity(int severity) {
        if ((severity == FAILURE)
                || (severity == WARNING)
                || (severity == OKAY))
            this._severity = severity;
    }

    public int getSeverity() {
        return _severity;
    }

    public String getSeverityString() {
        switch (_severity) {
            case FAILURE:
                return SEVERITY[0];
            case WARNING:
                return SEVERITY[1];
            case OKAY:
                return SEVERITY[2];
            default:
                return SEVERITY[3];
        }
    }

    public int getNagiosSeverity() {
        switch (_severity) {
            case FAILURE:
                return 2;
            case WARNING:
                return 1;
            case OKAY:
                return 0;
            default:
                return 3;
        }
    }

    public void setMessage(String message) {
        this._message = message;
    }

    public String getMessage() {
        return _message;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        sb.append(" [").append(getSeverityString()).append("] ");
        sb.append(_message);
        return sb.toString();
    }

    public String toNagiosString() {
        //[<timestamp>] PROCESS_SERVICE_CHECK_RESULT;<host_name>;<svc_description>;<return_code>;<plugin_output>
        StringBuilder sb = new StringBuilder('[')
                .append(getTime()).append("] ")
                .append("PROCESS_SERVICE_CHECK_RESULT;")
                .append(getHost()).append(';')
                .append(getPlugin()).append(';')
                .append(getNagiosSeverity()).append(';')
                .append(_message);
        return sb.toString();
    }
}
