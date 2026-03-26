package com.labsync.lms.dto.response;

import java.util.List;

/**
 * ClashReport DTO — details returned when schedule validation fails.
 * Lists each detected conflict so the caller can take corrective action.
 */
public class ClashReport {
    private boolean hasClash;
    private List<String> clashMessages;

    public static ClashReport noClash() {
        return ClashReport.builder().hasClash(false).clashMessages(List.of()).build();
    }

    public static ClashReport withClashes(List<String> messages) {
        return ClashReport.builder().hasClash(true).clashMessages(messages).build();
    }


    public static class ClashReportBuilder {
        private boolean hasClash;
        private List<String> clashMessages;

        ClashReportBuilder() {
        }

        /**
         * @return {@code this}.
         */
        public ClashReport.ClashReportBuilder hasClash(final boolean hasClash) {
            this.hasClash = hasClash;
            return this;
        }

        /**
         * @return {@code this}.
         */
        public ClashReport.ClashReportBuilder clashMessages(final List<String> clashMessages) {
            this.clashMessages = clashMessages;
            return this;
        }

        public ClashReport build() {
            return new ClashReport(this.hasClash, this.clashMessages);
        }

        @java.lang.Override
        public java.lang.String toString() {
            return "ClashReport.ClashReportBuilder(hasClash=" + this.hasClash + ", clashMessages=" + this.clashMessages + ")";
        }
    }

    public static ClashReport.ClashReportBuilder builder() {
        return new ClashReport.ClashReportBuilder();
    }

    public boolean isHasClash() {
        return this.hasClash;
    }

    public List<String> getClashMessages() {
        return this.clashMessages;
    }

    public void setHasClash(final boolean hasClash) {
        this.hasClash = hasClash;
    }

    public void setClashMessages(final List<String> clashMessages) {
        this.clashMessages = clashMessages;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof ClashReport)) return false;
        final ClashReport other = (ClashReport) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        if (this.isHasClash() != other.isHasClash()) return false;
        final java.lang.Object this$clashMessages = this.getClashMessages();
        final java.lang.Object other$clashMessages = other.getClashMessages();
        if (this$clashMessages == null ? other$clashMessages != null : !this$clashMessages.equals(other$clashMessages)) return false;
        return true;
    }

    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof ClashReport;
    }

    @java.lang.Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * PRIME + (this.isHasClash() ? 79 : 97);
        final java.lang.Object $clashMessages = this.getClashMessages();
        result = result * PRIME + ($clashMessages == null ? 43 : $clashMessages.hashCode());
        return result;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "ClashReport(hasClash=" + this.isHasClash() + ", clashMessages=" + this.getClashMessages() + ")";
    }

    public ClashReport(final boolean hasClash, final List<String> clashMessages) {
        this.hasClash = hasClash;
        this.clashMessages = clashMessages;
    }

    public ClashReport() {
    }
}
