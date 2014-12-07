package util;

import java.io.Serializable;
import java.util.Arrays;

public class Message implements Serializable {
    private static final long serialVersionUID = 7969249027230505910L;

    private final String objRef;
    private final String methodName;
    private final Object[] parameters;

    /**
     * Message constructor using object reference, methodName and parameters
     * to store object information to be transfered via a connection object
     *
     * @param identifier
     * @param methodName
     * @param parameters
     */
    public Message(String objRef, String methodName, Object[] parameters) {
        this.objRef     = objRef;
        this.methodName = methodName;
        this.parameters = parameters;
    }

    /**
     * Getter for object reference
     *
     * @return String
     */
    public String getObjRef() {
        return objRef;
    }

    /**
     * Getter for called method name
     *
     * @return String
     */
    public String getMethodName() {
        return methodName;
    }

    /**
     * Getter for array of parameters
     *
     * @return Object[]
     */
    public Object[] getParameters() {
        return parameters;
    }

    @Override
    public String toString() {
        return "[MESSAGE]: Object reference [" + objRef + "] | Method name ["
                + methodName + "] | Parameters ["
                + Arrays.toString(parameters) + "]";
    }

}
