using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Apple : MonoBehaviour
{
    // do p�l statycznych maj� dost�p wszystkie instancje kalsy. Zmina tego pola w powoduje zmian� we wszystkich instancjach :O !!!
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

            // uzuskaj referencj� do komponentu ApplePicker nale��cego do kamery g��wnej
            ApplePicker apScript = Camera.main.GetComponent<ApplePicker>();
            // wywo�aj publiczn� funkcj� AppleDestroyed() zawart� w skrypcie apScript
            apScript.AppleDestroyed();
        }
    }
}
