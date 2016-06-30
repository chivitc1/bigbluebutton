package org.bigbluebutton.common.messages2x.objects;

import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class ShapeAnnotation {

    public AnnotationType annotationType;
    public Double[] points;
    public Integer color;
    public Boolean transparency;
    public AnnotationStatus status;
    public String shapeID;
    public Integer thickness;

    public ShapeAnnotation(AnnotationType annotationType, Double[] points, Integer color,
                           Boolean transparency, AnnotationStatus status,
                           String shapeID, Integer thickness) {
        this.annotationType = annotationType;
        this.points = points;
        this.color = color;
        this.transparency = transparency;
        this.status = status;
        this.shapeID = shapeID;
        this.thickness = thickness;
    }

    public static ShapeAnnotation fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, ShapeAnnotation.class);
    }
}