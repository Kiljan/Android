using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Apple : MonoBehaviour
{
    // do pól statycznych maj¹ dostêp wszystkie instancje kalsy. Zmina tego pola w powoduje zmianê we wszystkich instancjach :O !!!
    public static float bottomY = -20f;

    // Start is called before the first frame update
    void Start()
    {
        
    }

    // Update is called once per frame
    void Update()
    {
        if (transform.position.y < bottomY)
        {
            Destroy(this.gameObject);

            // uzuskaj referencjê do komponentu ApplePicker nale¿¹cego do kamery g³ównej
            ApplePicker apScript = Camera.main.GetComponent<ApplePicker>();
            // wywo³aj publiczn¹ funkcjê AppleDestroyed() zawart¹ w skrypcie apScript
            apScript.AppleDestroyed();
        }
    }
}
