package com.deepayment.sdk;

/** Base type of the SDK's exceptions. */
public class DeepaymentException extends RuntimeException {

    public DeepaymentException(String message) {
        super(message);
    }

    public DeepaymentException(String message, Throwable cause) {
        super(message, cause);
    }

    /** Base type of protocol-level errors. */
    public static class Protocol extends DeepaymentException {
        public Protocol(String message) {
            super(message);
        }
    }

    /** Malformed signature headers, request shape or parameters. */
    public static final class InvalidHeader extends Protocol {
        public InvalidHeader(String message) {
            super(message);
        }
    }

    /** Malformed body envelope or decryption failure. */
    public static final class InvalidEnvelope extends Protocol {
        public InvalidEnvelope(String message) {
            super(message);
        }
    }

    /** Malformed signature or failed verification. */
    public static final class InvalidSignature extends Protocol {
        public InvalidSignature(String message) {
            super(message);
        }
    }

    /** Invalid or expired signature window. */
    public static final class ExpiredSignature extends Protocol {
        public ExpiredSignature(String message) {
            super(message);
        }
    }

    /** Missing or invalid configuration field. */
    public static final class Config extends DeepaymentException {
        public Config(String message) {
            super(message);
        }
    }

    /** The request was rejected before it was sent: local validation or a bad parameter. */
    public static final class Request extends DeepaymentException {
        public Request(String message) {
            super(message);
        }

        public Request(String message, Throwable cause) {
            super(message, cause);
        }
    }

    /**
     * No response was obtained after the request left the process (connection failure, timeout,
     * interrupted). The outcome is unknown: query the order before retrying.
     */
    public static final class Transport extends DeepaymentException {
        public Transport(String message, Throwable cause) {
            super(message, cause);
        }
    }

    /** Webhook verification or parsing failure. */
    public static final class Webhook extends DeepaymentException {
        public Webhook(String message) {
            super(message);
        }
    }

    /** Response body larger than maxResponseBytes. */
    public static final class ResponseTooLarge extends DeepaymentException {
        public ResponseTooLarge(String message) {
            super(message);
        }
    }

    /** The gateway returned a valid envelope carrying a business failure. */
    public static final class Api extends DeepaymentException {
        public final int httpStatus;
        public final int code;
        public final String msg;
        public final String apiMessage;
        public final String traceId;
        public final byte[] rawBody;

        public Api(int httpStatus, int code, String msg, String apiMessage, String traceId,
                byte[] rawBody) {
            super("sdk: http=" + httpStatus + " code=" + code + " msg=" + msg
                    + " traceId=" + traceId
                    + (apiMessage == null || apiMessage.isEmpty() ? "" : " message=" + apiMessage));
            this.httpStatus = httpStatus;
            this.code = code;
            this.msg = msg == null ? "" : msg;
            this.apiMessage = apiMessage == null ? "" : apiMessage;
            this.traceId = traceId == null ? "" : traceId;
            this.rawBody = rawBody;
        }
    }

    /** Response is not a valid envelope JSON (an HTML error page or plain-text 502 from the gateway or a CDN). */
    public static final class Response extends DeepaymentException {
        public final int httpStatus;
        public final byte[] rawBody;

        public Response(int httpStatus, byte[] rawBody) {
            super("sdk: http=" + httpStatus + " response is not a valid envelope JSON");
            this.httpStatus = httpStatus;
            this.rawBody = rawBody;
        }
    }
}
