
/**
 * CUSTOM TYPES
 *      - "type" or "interface"
 *          - interfaces are usually used for basic objects that map to some data
 *          - types can do more, ex: create unions, intersections, and sets between multiple types
 */

// export makes this type available in other files (can use imports to bring it in)
export interface Director {
    id?: number;        // ? - makes property optional
    firstName: string;
    lastName: string;
}